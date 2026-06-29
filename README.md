# Railway Management System

## Overview
A command-line railway management system developed for the Algorithms and Data Structures course at FCT NOVA. The system manages train lines, stations, and schedules, enabling users to insert, remove, and query railway data through a persistent interactive interface. All data structures are custom-implemented from scratch as part of the course requirements.

## Core Technologies
Built entirely in Java with no external libraries. Core data structures — hash tables with separate chaining, binary search trees, doubly-linked lists, and stacks — are custom-implemented to reinforce the course material. Data persistence is achieved through Java Object Serialization, restoring full system state between sessions.

## Key Capabilities

**Railway & Station Management**: Insert and remove railway lines, each defined by an ordered sequence of stations. The system maintains bidirectional mappings between stations and lines, enabling efficient cross-referencing queries in both directions.

**Schedule Operations**: Train schedules are validated against their railway's station sequence and time ordering constraints before insertion. Each schedule is stored in a BST keyed by departure time, enabling ordered retrieval and efficient queries.

**Best Route Queries**: Given two stations and a departure time, the system identifies the optimal train schedule — finding the earliest arrival time among all valid routes connecting those stations.

**Persistent State**: All system data is serialized to disk on exit and restored on startup, ensuring no data loss between sessions.

## Data Structures

| Structure | Implementation | Usage |
|---|---|---|
| Hash Table | Separate Chaining | Station and railway lookups by name |
| Binary Search Tree | Ordered Dictionary | Schedule ordering by departure time |
| Doubly-Linked List | Custom Iterator | Station sequences per railway |
| Stack | List-backed | Internal operations |

## Architecture Highlights
- Interface-segregated design separates query and mutation contracts (`Railway` / `UpdateRailway`, `Station` / `UpdateStation`)
- `TrainSystemClass` acts as the central facade, delegating operations to domain objects
- Time representation via `TrainDateClass` supports full ordering and difference computation in HH:MM format
- Generic data structures with typed interfaces (`Dictionary<K,V>`, `Iterator<E>`) ensure type safety across the codebase

## Project Structure

```
RailwayProject/
├── Main.java                    # Entry point and command-line interface
├── railwaySystem/               # Core domain model
│   ├── TrainSystem.java         # System facade interface
│   ├── TrainSystemClass.java    # System facade implementation
│   ├── Railway.java / RailwayClass.java
│   ├── Station.java / StationClass.java
│   ├── Schedule.java / ScheduleClass.java
│   ├── TrainDate.java / TrainDateClass.java
│   └── Arrival.java / ArrivalClass.java
├── dataStructures/              # Custom data structure implementations
│   ├── SepChainHashTable.java
│   ├── BinarySearchTree.java
│   ├── DoubleList.java
│   ├── StackInList.java
│   └── ...
└── exceptions/                  # Domain-specific exceptions
```

## Getting Started

**Requirements:** Java 8 or higher

**Compile:**
```bash
javac -d bin $(find . -name "*.java")
```

**Run:**
```bash
java -cp bin Main
```

**Available Commands:**

| Command | Description |
|---|---|
| `IL` | Insert railway line |
| `RL` | Remove railway line |
| `CL` | List stations of a railway |
| `IH` | Insert train schedule |
| `RH` | Remove train schedule |
| `CH` | Check schedule details |
| `MH` | Find best schedule between stations |
| `CE` | List railways passing through a station |
| `LC` | List all trains at a station |
| `TA` | Terminate application (saves state to disk) |

## Authors
- **Lourenço Beato** (68461) — lm.beato@campus.fct.unl.pt
- **Tomás Sousa** (68302) — tsm.sousa@campus.fct.unl.pt

FCT NOVA — Algorithms and Data Structures, 2024/2025

