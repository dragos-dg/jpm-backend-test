Feature: Users can comment on post on our website

  Scenario: Users can view existing comments
    Given a user has landed on our website
    Then the user should be able to view existing comments

  Scenario Outline: User post comments are correctly saved
    Given a user has landed on our website
    When a user adds a new comment against <postId> with "<name>", "<email>" and "<body>"
    Then the post will be saved with correct "<name>", "<email>" and "<body>" against correct <postId>

    Examples:
      | postId | name             | email             | body            |
      | 7      | great post       | user5@email.com   | some nice words |
      | 12     | not feeling well | user10@email.com  | good post       |
      | 22     | some text        | user1@email.com   |                 |
      | 111    |                  | user999@email.com | some nice words |