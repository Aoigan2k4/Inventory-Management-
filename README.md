# Inventory Management Application
Authors: Tran Ba Minh Huy

This project implements a user authentication and item-building system using Firebase Authentication and Firestore, along with a builder pattern for item creation. The system includes user registration, login, and item factory classes.

Technologies Used
- Java
- Firebase Authentication
- Firebase Firestore
- OOP Design Patterns (Singleton, Builder, Abstract Factory)
- Project Structure

Authentication

- SignUp: Handles user registration.
- LogIn: Handles user login.
- FirebaseManager: Singleton class to manage Firebase Authentication and Firestore database access.
- User: Represents a registered user with id, username, and email.

Item Management

- AbstractItem: An abstract factory class for building different types of items.
- ClothingFactory, ElectronicFactory, FurnitureFactory: Implement AbstractItem for creating different item types.
- Type (Enum): Defines different item types (electronic, clothing, furniture).
- IBuilder: Interface defining the item-building methods.
- ClothingBuilder, ElectronicBuilder, FurnitureBuilder: Implement the IBuilder interface to construct different item types.
- Engineer: Uses an IBuilder to build an item.
- Items: Represents an item with attributes such as id, name, brand, price, desc, and quantity.

