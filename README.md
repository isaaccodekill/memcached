# Project Title: Java-Based Memcached-Like Server

## Introduction
This Java-based server mimics the functionality of Memcached, focusing on basic caching operations over a TCP connection. The implementation leverages the Vert.x framework to efficiently handle TCP requests, following a simplified version of the Memcached protocol. Supported commands include `set`, `get`, `delete`, `flush_all`, and `help`.

## Features
- TCP server implementation using Vert.x
- Basic Memcached command support: `set`, `get`, `delete`, `flush_all`, `help`
- Efficient handling of TCP data fragmentation
- Simple command parsing with extensibility

## Installation
Follow these steps to set up the server:

### Prerequisites
- Java JDK 11 or higher
- Gradle (for building the project)

### Building the Project
1. Clone the repository:
   ```sh
   git clone https://github.com/isaaccodekill/memcached.git
   ```
2. Navigate to the project directory:
   ```sh
   cd memcached
   ```
3. Build the project with Gradle:
   ```sh
   gradle build
   ```

## Usage
After building the project, start the server with:

```sh
  gradle run
```

The server listens on a configurable default port, as set in the application's configuration.

### Supported Commands
- `set <key> <flags> <exptime> <bytes> [noreply]\r\n<data block>\r\n` [coming soon]
- `get <key>*\r\n` [coming soon]
- `delete <key> [noreply]\r\n` [coming soon]
- `flush_all [noreply]\r\n` [coming soon]
- `help\r\n`

## Contributing
We welcome contributions. Please adhere to these guidelines:
- Fork the repository and create a new branch for your feature or fix.
- Write clean, commented, and well-tested code.
- Update the README.md if necessary.
- Submit a pull request with a clear description of your changes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.