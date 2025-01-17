Overview

This Java-based application manages car rental reservations, providing CRUD operations, reservation management, reporting, and multi-format data persistence. It uses a layered architecture and includes features like data validation, filtering, and JavaFX GUI.

Key Features

Layered Architecture: Includes Domain, Repository, Service, and UI layers.

CRUD Operations: Supported for Cars and Reservations.

Reservation Management: Created and canceled reservations with status tracking.

Reports: Generated 5+ reports using Java 8 Streams (e.g., cars rented by a person, reservation details).

Data Filtering: Filtered entities using a generic AbstractFilter interface.

Validation: Used custom Validator classes for input integrity.

Persistence: Supported text files, binary files, relational databases, JSON, and XML, configurable via settings.properties.

JavaFX GUI: Developed a graphical interface replicating console UI functionality.

Testing: Achieved 95%+ test coverage for at least one entity across layers (excluding UI).
