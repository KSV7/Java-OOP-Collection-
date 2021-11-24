package com.gmail.kutepov89.sergey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Group {
	private String name;

	private ArrayList<Student> students = new ArrayList<Student>();

	public static Comparator<Student> StuNameComparator = new Comparator<Student>() {
		public int compare(Student s1, Student s2) {
			String StudentName1 = s1.getLastName().toUpperCase();
			String StudentName2 = s2.getLastName().toUpperCase();
			return StudentName1.compareTo(StudentName2);
		}
	};

	public Group(String name, ArrayList<Student> students) {
		super();
		this.name = name;
		this.students = students;
	}

	public Group(String name) {
		super();
		this.name = name;
	}

	public Group() {
		super();
	}

	public void add(Student student) {
		students.add(student);
	}

	public String del(String lastName) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i) != null && students.get(i).getLastName().equalsIgnoreCase(lastName)) {
				String st = students.get(i).getLastName();
				students.remove(i);
				return "студент " + st + " удален";
			}
		}
		return "Такого студента нет в списке";
	}

	public Student searchByLastName(String lastName) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i) != null && students.get(i).getLastName().equalsIgnoreCase(lastName)) {
				return students.get(i);
			}
		}
		return null;
	}



	public void saveListStudentsToFile(String fileName) {
		String res = "";
		StringBuilder stringBuilder = new StringBuilder();
		Collections.sort(students, Group.StuNameComparator);

		for (Student s : students) {
			if (s != null) {
				stringBuilder
						.append(s.getLastName() + " " + s.getFirstName() + " " + s.getPatronymic() + " " + s.getAge())
						.append("\n");
			}
		}
		res = stringBuilder.toString();

		try (PrintWriter a = new PrintWriter(fileName)) {
			a.println(res);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR FILE WRITE");
		}
	}

	public void readListStudentsFromFile(String fileName, Group group) {
		try (BufferedReader f = new BufferedReader(new FileReader(fileName))) {
			String str = "";
			for (; (str = f.readLine()) != null;) {
				if (!str.isEmpty()) {
					String[] student = str.split(" ");
					Student st = new Student(student[0], student[1], student[2], Integer.parseInt(student[3]), true, 1);
					group.add(st);
				}
			}
		} catch (IOException e) {
			System.out.println("ERROR");
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		Collections.sort(students, Group.StuNameComparator);

		stringBuilder.append("\n");
		stringBuilder.append("Group name: " + name);
		stringBuilder.append("\n");
		for (Student s : students) {
			if (s != null) {
				stringBuilder.append(s.getLastName() + " " + s.getFirstName() + " (age: " + s.getAge() + ")")
						.append("\n");
			}
		}
		stringBuilder.append("\n");

		return stringBuilder.toString();
	}

}