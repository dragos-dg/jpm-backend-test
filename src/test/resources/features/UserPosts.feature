Feature: Users can view and post to our website

  Scenario: Users can view existing posts
    Given a user has landed on our website
    Then the user should be able to view existing posts

  Scenario Outline: User posts are correctly saved
    Given a user has landed on our website
    When the user <userId> creates a new post with "<title>" and "<body>"
    Then the post will be saved with correct <userId>, "<title>" and "<body>"

    Examples:
      | userId | title            | body            |
      | 5      | my title         | some nice words |
      | 10     | not feeling well |                 |
      | 1      |                  |                 |
      | 999    |                  | some nice words |
