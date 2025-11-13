# ByteWarden 🔐

![Tests](https://github.com/Bilnaa/ByteWarden/actions/workflows/tests.yml/badge.svg)

**ByteWarden** is a comprehensive password management system built in Java that provides secure storage and encryption of passwords using multiple cryptographic algorithms. This project was developed as part of a school assignment to demonstrate understanding of encryption techniques, data security, and software engineering principles.

## 📋 Table of Contents

- [Features](#features)
- [Encryption Algorithms](#encryption-algorithms)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Contributors](#contributors)
- [License](#license)

## ✨ Features

- **Database Management**: Create and manage multiple encrypted databases
- **Site Management**: Store, modify, and delete website credentials (username/password pairs)
- **Multiple Encryption Methods**: Support for various classical and modern encryption algorithms
- **Secure Password Storage**: Passwords are hashed using SHA-256 with salt and pepper
- **Interactive CLI**: User-friendly command-line interface with animated splash screen
- **Help System**: Built-in help menu with detailed explanations
- **Steganography**: Hide data within images, text, and video files

## 🔒 Encryption Algorithms

ByteWarden supports the following encryption algorithms:

- **ROTX**: Caesar cipher variant that shifts characters by X positions
- **RC4**: Stream cipher for fast encryption/decryption
- **Vigenère**: Polyalphabetic substitution cipher using a keyword
- **Polybios Square**: Encoding method using a 5x5 grid
- **Enigma Machine**: Simulation of the historical WWII encryption machine with rotors, reflectors, and plugboard
- **Steganography**: Hide information within images, text, or video files

### Hashing Algorithms

- **MD5**: Message Digest algorithm (for educational purposes)
- **SHA-256**: Secure Hash Algorithm for password hashing

### Additional Features

- **LFSR**: Linear Feedback Shift Register for pseudorandom number generation
- **Password Utilities**: Random password generation and validation

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven or Gradle (for dependency management)
- Google Gson library (for JSON handling)
- Jackson library (for JSON processing)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Bilnaa/ByteWarden.git
cd ByteWarden
```

2. Compile the project:
```bash
javac -d out/production/ByteWarden -cp "path/to/gson.jar:path/to/jackson.jar" src/**/*.java
```

3. Run the application:
```bash
java -cp "out/production/ByteWarden:path/to/gson.jar:path/to/jackson.jar" Main
```

Or use the pre-built JAR file:
```bash
java -jar out/artifacts/ByteWarden/ByteWarden.jar
```

## 📖 Usage

### Creating a Database

1. Launch the application
2. Select option `2` to create a new database
3. Enter a database name
4. Choose a password (custom or auto-generated)
5. Select one or more encryption methods to use for your passwords
6. Start adding sites to your database

### Managing Sites

Once connected to a database, you can:
- **Add a site**: Store new website credentials
- **Modify a site**: Update existing username or password
- **Delete a site**: Remove a site from your database
- **Display all sites**: View all stored credentials

### Encryption Methods

When creating a database, you can choose from:
- **RotX**: Requires a shift value (e.g., ROT13 uses shift 13)
- **RC4**: Requires an encryption key
- **Vigenère**: Requires a keyword
- **Polybios**: Uses default configuration

Multiple encryption methods can be combined for enhanced security.

## 📁 Project Structure

```
ByteWarden/
├── src/
│   ├── Main.java                    # Application entry point
│   ├── Classes/
│   │   ├── Menu.java                # Main menu and UI
│   │   ├── HelpMenu.java            # Help system
│   │   ├── DatabasesManager.java    # Database management
│   │   ├── SiteManager.java         # Site credential management
│   │   ├── PasswordUtils.java       # Password utilities
│   │   ├── ROTX.java                # ROTX encryption
│   │   ├── RC4.java                 # RC4 encryption
│   │   ├── VigenereAlgo.java        # Vigenère cipher
│   │   ├── PolybSquareEncrypter.java # Polybios square
│   │   ├── MD5.java                 # MD5 hashing
│   │   ├── Sha256.java              # SHA-256 hashing
│   │   ├── Hash.java                # Hash interface
│   │   ├── Lfsr.java                # Linear Feedback Shift Register
│   │   ├── Enigma/                  # Enigma machine implementation
│   │   │   ├── Enigma.java
│   │   │   ├── Rotors.java
│   │   │   ├── Reflector.java
│   │   │   └── Plugboard.java
│   │   └── Steganography/           # Steganography implementation
│   │       ├── Steganography.java
│   │       ├── Image.java
│   │       ├── Text.java
│   │       └── Video.java
│   └── Tests/                       # Unit tests
│       ├── *Test.java               # Test files for each component
│       └── assets/                  # Test assets
├── databases.json                   # Database metadata storage
└── README.md                        # This file
```

## 🧪 Testing

The project includes comprehensive unit tests for all major components. Run tests using your preferred testing framework (JUnit recommended).

Test coverage includes:
- Encryption algorithms
- Hashing functions
- Database management
- Site management
- Steganography operations

## 👥 Contributors

This project was developed as a collaborative school project by:

- **Nabil** - [@Bilnaa](https://github.com/Bilnaa)
- **Paul Rivallin** - [@Roronoatii](https://github.com/Roronoatii)
- **Lucas** - [@lucasTrswl](https://github.com/lucasTrswl)

## 🎓 School Project

This project was developed as part of a school assignment to demonstrate:
- Understanding of cryptographic algorithms and their implementations
- Object-oriented programming principles in Java
- Secure software development practices
- File I/O and data persistence
- Software testing methodologies

## 📝 License

This project is developed for educational purposes as part of a school assignment.

---

**Note**: This password manager is intended for educational purposes. For production use, consider using established, audited password managers with modern cryptographic standards.
