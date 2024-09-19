# Health App API

API for the Health app project.

## Description

Super Health Inc. is a small regional healthcare company that operates a series of clinics. The
company has an existing application for tracking patient encounter data that has been in service for
a number of years and needs a rewrite. Super Health has hired you to rewrite the application in a
modern way using the technologies that you have trained in. At this point, Super Health Inc. is
looking for a proof of concept and will not require any authentication or authorization. The
database stores patient and encounter data, and its design is up to you. Any user can review,
create, and update patient information and encounters. The client has expressed that they would like
the project to be documented and easily maintainable.

## Pre-Requisites
* Text editor for API (IntelliJ recommended).
* Postman can be used to simulate the front-end framework.

## Usage

This API will be hosted on localhost:8080 and requires access to a Postgres database on port 5432.

### Starting the Application with Postgres

* Ensure that your postgres database is available and configured with the following options:
    * POSTGRES_USER=postgres
    * POSTGRES_PASSWORD=root
    * PORT=5432
* The DataLoader class in the edu.mtc.training.data package will load a few examples of each
  entity (Users, Reservation, Room Type) into the database after the service starts up.

### Running the Application

* Navigate to src\main\java\edu\mtc\training for AppRunner.java
* If starting in Intellij right click Application, then click run.
* After this has been done, the application may be run subsequently with the green play symbol in
  the top right corner. Ensure the dropdown selection is at AppRunner.

### Patient API

| If you want to...    | Use this method... | And this URI...                     |
|----------------------|--------------------|-------------------------------------|
| Create a patient     | POST               | http://localhost:8080/patients      |
| Read all patients    | GET                | http://localhost:8080/patients      |
| Read a patient by id | GET                | http://localhost:8080/patients/{id} |
| Update a patient     | PUT                | http://localhost:8080/patients/{id} |
| Delete a patient     | DELETE             | http://localhost:8080/patients/{id} |

#### Parameters

The Reservation object has the following properties:

* **id**. Long. Number. The unique identifier for a record in the database. The id is
  auto-generated, so
  you do not need to include it when creating a new patient.
* **firstName**. String. The patient's first name.
* **lastName**. String. The patient's last name.
* **ssn**. String. The patient's social security number. Must be in format(XXX-XX-XXXX)
* **email**. String. The patient's email address.
* **street**. String. The patient's street address.
* **city**. String. The patient's city.
* **state**. String. The patient's state. Two character code
* **postal**. String. The patient's zip code. Must be in format XXXXX or XXXXX-XXXX
* **age**. String. The patient's age.
* **height**. String. The patient's height in inches.
* **weight**. String. The patient's weight in pounds.
* **insurance**. String. The patient's insurance provider.
* **gender**. String. The patient's gender. Valid Inputs: "Male", "Female", "Other"

Example JSON object to use when creating a patient (POST):

````
{ 
 "firstName": "Hulk",
 "lastName": "Hogan",
 "ssn": "123-45-6789",
 "email": "hulksnewemailaddress@wwf.com",
 "age": 66,
 "height": 79,
 "weight": 299,
 "insurance": "Self-Insured",
 "gender": "Male",
 "street": "8430 W Sunset Blvd",
 "city": "Los Angeles",
 "state": "CA",
 "postal": "90049" 
} 
````

Example JSON object to use when updating a patient (PUT) - must include id property:

````
{ 
 "id" : 1
 "firstName": "Hulk",
 "lastName": "Hogan",
 "ssn": "123-45-6789",
 "email": "hulksnewemailaddress@wwf.com",
 "age": 66,
 "height": 79,
 "weight": 299,
 "insurance": "Self-Insured",
 "gender": "Male",
 "street": "8430 W Sunset Blvd",
 "city": "Los Angeles",
 "state": "CA",
 "postal": "90049" 
}
````

#### Response Messages

GET - Fetch all patients or fetch a patient by id

| HTTP Status Code | Reason                                                                                                            |
|------------------|-------------------------------------------------------------------------------------------------------------------|
| 200              | OK - If no id provided, returns all patients. If id is provided, returns a single patient associated with that id |
| 404              | Not Found - The id for that patient does not exist                                                                |

POST - Creating a patient

| HTTP Status Code | Reason                                                                                     |
|------------------|--------------------------------------------------------------------------------------------|
| 201              | Created - Returns created JSON object                                                      |
| 400              | Bad Request - Check your inputs for invalid entries                                        |

PUT - Updating a patient

| HTTP Status Code | Reason                                                                                     |
|------------------|--------------------------------------------------------------------------------------------|
| 200              | OK - Returns updated JSON object                                                           |
| 400              | Bad Request - Check your inputs for invalid entries                                        |

DELETE - Deleting a patient

| HTTP Status Code | Reason                                             |
|------------------|----------------------------------------------------|
| 204              | No Content - Deletes patient                       |
| 404              | Not Found - The id for that patient does not exist |

### Encounter API

| If you want to...       | Use this method... | And this URI...                                            |
|-------------------------|--------------------|------------------------------------------------------------|
| Create an encounter     | POST               | http://localhost:8080/patients/{patientId/encounters       |
| Read all encounters     | GET                | http://localhost:8080/patients/{patientId}/encounters      |
| Read an encounter by id | GET                | http://localhost:8080/patients/{patientId}/encounters/{id} |
| Update an encounter     | PUT                | http://localhost:8080/patients/{patientId}/encounters/{id} |

#### Parameters

The Room Type object has the following properties:

* **id**. Long. Number. The unique identifier for a record in the database. The id is
  auto-generated, so
  you do not need to include it when creating a new room type.
* **patientId**. Long. Number. The id of the patient associated with the encounter.
* **notes**. String. Optional. The notes associated with the encounter.
* **visitCode**. String. Office visit code. Must be in format: (LDL DLD) L=letter D=numeric digit
* **provider**. String. The name of provider.
* **billingCode**. String. The encounter billing code. Must be in format(XXX.XXX.XXX-XX)
* **icd10**. String. The icd10 associated with the encounter. Format(LDD) L=letter D=numeric digit
* **totalCost**. Double. Total cost of encounter including copay in US dollars.
* **copay**. Double. Patient's copay for encounter in US dollars.
* **chiefComplaint**. String. Initial complaint of the patient at the start of the encounter.
* **pulse**. Integer. Number. Optional. Patient's pulse in beats per minute.
* **systolic**. Integer. Number. Optional. Systolic portion of blood pressure.
* **diastolic**. Integer. Number. Optional. Diastolic portion of blood pressure.
* **Date**. Date. The date of the encounter in ISO 8601. Format: YYYY-MM-DD

Example JSON object to use when creating an encounter (POST):

````
{
    "patientId": 1,
    "notes": "new encounter",
    "visitCode": "N3W 3C3",
    "provider": "New Hospital",
    "billingCode": "123.456.789-00",
    "icd10": "Z99",
    "totalCost": 0.11,
    "copay": 0,
    "chiefComplaint": "new complaint",
    "pulse": "",
    "systolic": "",
    "diastolic": "",
    "date": "2020-08-04" 
}
````

Example JSON object to use when updating an encounter (PUT) - must include id property:

````
{
    "id": 1,
    "patientId": 1,
    "notes": "new encounter",
    "visitCode": "N3W 3C3",
    "provider": "New Hospital",
    "billingCode": "123.456.789-00",
    "icd10": "Z99",
    "totalCost": 0.11,
    "copay": 0,
    "chiefComplaint": "new complaint",
    "pulse": "",
    "systolic": "",
    "diastolic": "",
    "date": "2020-08-04"
}
````

#### Response Messages

GET - Fetch all encounters or fetch an encounter by id

| HTTP Status Code | Reason                                                                                                                |
|------------------|-----------------------------------------------------------------------------------------------------------------------|
| 200              | OK - If no id provided, returns all encounters. If id is provided, returns a single encounter associated with that id |
| 404              | Not Found - The id for that room type does not exist                                                                  |

POST - Creating an encounter

| HTTP Status Code | Reason                                                                                       |
|------------------|----------------------------------------------------------------------------------------------|
| 201              | Created - Returns created JSON object                                                        |
| 400              | Bad Request - Check your inputs for invalid entries                                          |

PUT - Updating an encounter

| HTTP Status Code | Reason                                                                                       |
|------------------|----------------------------------------------------------------------------------------------|
| 200              | OK - Returns updated JSON object                                                             |
| 400              | Bad Request - Check your inputs for invalid entries                                          |

## Testing

Running Mockito Unit Tests

* Look Under src\test\java\edu\mtc\training for the services package
* Right click services and run tests in edu.mtc.training.services
* Code coverage can be viewed by right-clicking on the service package and selecting Run with
  Coverage.
* Coverage can also be run subsequently in the top right corner white and green shield symbol.
* Unit tests cover all services.

Running Integration Tests

* Start the container.
* Look Under src\test\java\edu\mtc\training for the services package
* Right click controllers and run tests in edu.mtc.training.services
* Integration tests cover payload, content type, and status code tests of all 2XX
* Code coverage can be viewed by right-clicking on the controller package and selecting Run with
  Coverage.
* Coverage can also be run subsequently in the top right corner white and green shield symbol.
* Real data is manipulated in my integration tests.

## Linting

While document is open, press `CTRL + ALT + L` to lint the selected document

## Credits

Curtis Lynn

4/26/2024

Create Opportunity September 2023 Cohort

Module 7: Final Project