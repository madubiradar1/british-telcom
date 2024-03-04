package com.bt.ne;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BTApplication {

	private static List<Person> persons;
	private static List<NetworkElement> networkElements;
	private static List<Links> links;

	private static Map<String, List<Links>> networkGraph;

	public BTApplication() {
		this.persons = new ArrayList<>();
		this.networkElements = new ArrayList<>();
		this.links = new ArrayList<>();
		this.networkGraph = new HashMap<>();
	}
	private static void readPersonDataFromCSV(String filePath) {

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> records = reader.readAll();

			for (String[] record : records) {
				String name = record[0];
				String exchange = record[1];
				persons.add(new Person(name, exchange));
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	private static void readNetworkElementDataFromCSV(String filePath) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> records = reader.readAll();

			for (String[] record : records) {
				String name = record[0];
				int processingTime = Integer.parseInt(record[1]);
				networkElements.add(new NetworkElement(name, processingTime));
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	private static void readLinkDataFromCSV(String filePath) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> records = reader.readAll();

			for (String[] record : records) {
				String source = record[0];
				String destination = record[1];
				int price = Integer.parseInt(record[2]);
				links.add(new Links(source, destination, price));
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		readPersonDataFromCSV("person.csv");
		readNetworkElementDataFromCSV("network_element.csv");
		readLinkDataFromCSV("link.csv");

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter Source Person: ");
		String inSourcePersonName = scanner.nextLine();
		System.out.print("Enter Destination Person: ");
		String inDestinationPersonName = scanner.nextLine();

		Person person = new Person();
		Person source = person.findPersonByName(inSourcePersonName);
		Person destination = person.findPersonByName(inDestinationPersonName);

		buildNetworkModel();
		findLeastCostRoute(source, destination);

	}


	private static void buildNetworkModel(){

		for (Links link : links) {
			networkGraph.computeIfAbsent(link.source, key -> new ArrayList<>()).add(link);
			networkGraph.computeIfAbsent(link.destination, key -> new ArrayList<>()).add(link);
		}

	}

	static void findLeastCostRoute(Person sourcePerson, Person destinationPerson) {

		// Dijkstra's algorithm to find out shortest path on least cost
		Map<String, Integer> distance = new HashMap<>();
		Map<String, String> previous = new HashMap<>();
		PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

		// Initialization
		for (NetworkElement ne : networkElements) {
			distance.put(ne.name, Integer.MAX_VALUE);
			previous.put(ne.name, null);
		}
		distance.put(sourcePerson.exchange, 0);
		priorityQueue.add(sourcePerson.exchange);

		//
		while (!priorityQueue.isEmpty()) {
			String current = priorityQueue.poll();

			if (networkGraph.containsKey(current)) {
				for (Links link : networkGraph.get(current)) {
					String neighbor = link.source.equals(current) ? link.destination : link.source;

					int newDistance = distance.get(current) + link.price;
					if (newDistance < distance.get(neighbor)) {
						distance.put(neighbor, newDistance);
						previous.put(neighbor, current);
						priorityQueue.add(neighbor);
					}
				}
			}
		}


		//recreating path
		List<String> path = new ArrayList<>();
		String current = destinationPerson.exchange;
		while (current != null) {
			path.add(current);
			current = previous.get(current);
		}
		Collections.reverse(path);

		// Calculate total processing time and total price
		int totalProcessingTime = 0;
		int totalPrice = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			String currentNE = path.get(i);
			String nextNE = path.get(i + 1);

			for (Links link : networkGraph.get(currentNE)) {
				if ((link.source.equals(currentNE) && link.destination.equals(nextNE)) ||
						(link.source.equals(nextNE) && link.destination.equals(currentNE))) {
					totalProcessingTime += networkElements.stream()
							.filter(ne -> ne.name.equals(nextNE))
							.findFirst()
							.map(ne -> ne.processingTime)
							.orElse(0);
					totalPrice += link.price;
					break;
				}
			}
		}

		// Calculate the final cost using the given formula
		int totalCost = (5 * totalProcessingTime) + (2 * totalPrice);

		// Print the details of the route
		System.out.println("Least Cost Route:");
		System.out.println("Path: " + path);
		System.out.println("Total Processing Time: " + totalProcessingTime + " ms");
		System.out.println("Total Price: " + totalPrice + " pence");
		System.out.println("Total Cost: " + totalCost + " units");
	}

}

