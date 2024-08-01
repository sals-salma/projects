# Sustainable Foraging Plan

## High Level Requirements
- Configure Dependency Injection w/ annotations
- **Review** functionality of working features and fix issues:
- **Implement** incomplete/missing features:
- Make **Tests** 

#### Configure Dependency Injection w/ annotations 

#### REVIEW (run app to check if we have all our requirements implemented; if missing surround w/ *[missing/comments]* and add to implement)
- Items
  - Name is required.
  - Name cannot be duplicated [not missing but waits until the end to tell user name is duplicate].
  - Category is required.
  - Dollars/Kg is required.
  - Dollars/Kg must be between $0 (inedible, poisonous) and $7500.
  - Item ID is a system-generated unique sequential integer.
- View Items 

- Foragers [missing ENTIRELY!]
  - First name is required. 
  - Last name is required.
  - State is required.
  - The combination of first name, last name, and state cannot be duplicated.
  - Forager ID is a system-generated GUID (globally unique identifier).


- Forage 
  - Item is required and must exist. [not missing but instead of looping immediately exits back to menu]
  - Forager is required and must exist. 
  - Date is required and must not be in the future. [not missing but waits until the end to tell user date is in future]
  - Kilograms must be a positive number not more than 250.  
  - The combination of date, Item, and Forager cannot be duplicated. (Can't forage the same item on the same day. It should be tracked as one Forage.) [missing]
  - Forage ID is a system-generated GUID (globally unique identifier).
- View Forages by Date check

#### IMPLEMENT (implement missing/incomplete features; add whatever else we identify is missing/incomplete from our review)
- Add a Forager.(almost done; need help w/ duplicate condition)
  - First name is required.
  - Last name is required.
  - State is required.
  - The combination of first name, last name, and state cannot be duplicated.
  - Forager ID is a system-generated GUID (globally unique identifier).
- View Foragers by State (Done)
- Create a report that displays the kilograms of each Item collected on one day.
- Create a report that displays the total value of each Category collected on one day.

- Forage [duplicate is not implemented] (need help)
  - The combination of date, Item, and Forager cannot be duplicated. (Can't forage the same item on the same day. It should be tracked as one Forage.) 

#### Testing (write tests for every feature in IMPLEMENT) 

#### Technical Requirements
- Configure Dependency Injection w/ annotations

- By using either streams or loops:
  - (Ultimate Goal:**using data to create accurate reports**)
    - Generate the kilogram/item report 
    - Generate total value/category report 

- File Format:
  - Do NOT change delimiter or header
    - If possible, prevent commas from being added to data.
  - Forage Data
    - Each day's data is stored in a separate file with the naming convention: **yyyy-MM-dd.csv**

- Testing:
  - All new features must be tested.
  - Not responsible for creating testing for existing features unless you find a bug.
    - If you find a bug, please create a test to confirm the expected behavior and prevent regressions.

