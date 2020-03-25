package org.craftedsw.tripservicekata.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.craftedsw.tripservicekata.trip.TripServiceTest.UserBuilder;
import org.junit.jupiter.api.Test;

public class UserTest {

  private static final User BOB = new User();
  private static final User PAUL = new User();

  @Test
  void should_inform_when_users_are_not_friends() {
    User user = UserBuilder.aUser()
        .friendsWith(BOB)
        .build();

    assertThat(user.isFriendWith(PAUL), is(false));
  }

  @Test
  void should_inform_when_users_are_friends() {
    User user = UserBuilder.aUser()
        .friendsWith(BOB, PAUL)
        .build();

    assertThat(user.isFriendWith(PAUL), is(true));
  }
}
