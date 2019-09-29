Feature: Users can view and create a profile on our website

  Scenario: Users can view existing user profiles
    Given a user has landed on our website
    Then the user should be able to view existing user profiles

  Scenario: User can register a new profile
    Given a user has landed on our website
    Then the user can create a new profile successfully using following data
      | name     | username | email             | phone      | website    | address.street | address.suite | address.city | address.zipcode | address.geo.lat | address.geo.lng | company.name | company.catchPhrase | company.bs  |
      | John Doe | johndoe  | johndoe@email.com | 0123456789 | www.jd.com | Cook Street    | 7B            | London       | W1 3BU          | 51.5029686      | -0.0229433      | GoodCompany  | Can't think of one  | lorem ipsum |