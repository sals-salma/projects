# Solar Farm Plan

## High-level Requirements
User is a solar farm administrator.
- Add a solar panel to the farm.
- Update a solar panel.
- Remove a solar panel.
- Display all solar panels in a section.


## Solar Panels

### Data
- **Section**: name that identifies where the panel is installed.
- **Row**: the row number in the section where the panel is installed.
- **Column**: the column number in the section where the panel is installed.
- **Year Installed**
- **Material**: multicrystalline silicon, monocrystalline silicon, amorphous silicon, cadmium telluride, or copper indium gallium selenide.
- **Is Tracking**: determines if the panel is installed with sun-tracking hardware.
  Material must be a Java enum.

### Validation
- **Section** is required and cannot be blank.
- **Row** is a positive number less than or equal to 250.
- **Column** is a positive number less than or equal to 250.
- **Year Installed** must be in the past.
- **Material** is required and can only be one of the five materials listed.
- **Is Tracking** is required.
- The combined values of **Section**, **Row**, and **Column** may not be duplicated.

## Technical Requirements
- Three layer architecture
- Data stored in a delimited file.
- Repositories should throw a custom exception, never file-specific exceptions.
- Repository and service classes must be fully tested with both negative and positive cases. Do not use your "production" data file to test your repository.
- Solar panel material should be a Java enum with five values.

## Package/Class Overview
```
src
├───main
│   └───java
│       └───learn
│           └───solar
│               │   App.java                      -- app entry point
│               │
│               ├───data
│               │       DataException.java        -- data layer custom exception
│               │       PanelFileRepository.java  -- concrete repository
│               │       PanelRepository.java      -- repository interface
│               │
│               ├───domain
│               │       PanelResult.java          -- domain result for handling success/failure
│               │       PanelService.java         -- panel validation/rules
│               │
│               ├───models
│               │       Material.java             -- enum representing the 5 materials
│               │       Panel.java                -- solar panel model
│               │
│               └───ui
│                       Controller.java           -- UI controller
│                       View.java                 -- all console input/output
│
└───test
    └───java
        └───learn
            └───solar
                ├───data
                │       PanelFileRepositoryTest.java    -- PanelFileRepository tests
                │       PanelRepositoryTestDouble.java  -- helps tests the service, implements PanelRepository
                │
                └───domain
                        PanelServiceTest.java           -- PanelService tests
```

## Steps
1. Create a Maven project.
2. Add Maven dependencies to support unit testing.
3. Create packages.
4. Create the `Panel` model.
5. Create the `Material` enum.
6. Create the data layer's `DataException` custom exception.
7. Create the `PanelFileRepository` class. (switch w/ 8)
   All methods should catch IOExceptions and throw the data layer's `DataException` custom exception.
    - add a field for the file path and create a constructor to initialize the field
    - generate tests for the repository
    - create a `data` directory in the project root. add test, seed, and production data files
    - implement and test all of the needed repository methods
    - be sure to establish known-good-state with `@BeforeAll`
8. Extract an interface `PanelRepository` (IntelliJ: Refactor -> Extract Interface) from the `PanelFileRepository` class.
9. Create a `PanelResult` class.
10. Create the `PanelService` class.
    - add a field for the `PanelRepository` (not the `PanelFileRepository` class!) with a corresponding constructor
    - generate tests for the service
    - create a `PanelRepositoryTestDouble` class to support service testing, this test class implements the `PanelRepository` interface
    - implement and test all of the needed service methods
11. Create `View`
    - add `Scanner` field
    - create read* methods: `readString`, `readRequiredString`, `readInt`, `readInt` (with min/max)
12. Create `Controller`
    - add fields for service and view with corresponding constructor
    - add a `run` method
13. Create `App` and the `main` method.
    - instantiate all required objects: repository, service, view, and controller
    - run the controller
14. Work back and forth between controller and view.
    Run early and often. Add `System.out.println`s as breadcrumbs in controller, but don't forget to remove them when development is complete.
    Determine the correct sequence for service calls and view calls. What is the order?
    - implement the menu
    - implement the "view by section" feature
    - implement the "add panel" feature
    - implement the "update panel" feature
    - implement the "delete panel" feature

## Controller Perspectives

### View Panels by Section
1. collect section name from the view
2. use the name to fetch panels from the service
3. use the view to display panels

### Add a Panel
1. collect a complete and new panel from the view
2. use the service to add the panel and grab its result
3. display the result in the view

### Update a Panel
1. collect section name from the view
2. use the name to fetch panels from the service
3. display the panels in the view and allow the user to choose a panel (if no panel selected, abort)
4. update panel properties (setters) in the view
5. use the service to update/save the panel and grab its result
6. display the result in the view

### Delete a Panel
1. collect section name from the view
2. use the name to fetch panels from the service
3. display the panels in the view and allow the user to choose a panel (if no panel selected, abort)
4. use the service to delete the panel by its identifier
5. display success or failure in the view