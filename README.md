# bookstore

GUI-based bookstore application built in Java using Java Swing

## features

Single-window GUI using CardLayout
Two user roles: Owner and Customer, with unique screens
Owner can add and delete books and customers
Customers can browse books, buy, and collect loyalty points
Customers have either silver or gold loyalty status based on points
Books and Customers saved to and loaded from text files on startup and exit

## state design pattern

Project implemented using state design pattern to manage customer loyalty status
Silver and Gold state automatically changes based on assigned state object

## guide

Open project in Apache NetBeans
Run BookStoreApp.java
Login as owner with "admin" username and password
All data is saved to "books.txt" and "customers.txt"
