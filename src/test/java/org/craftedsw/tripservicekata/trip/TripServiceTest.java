package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TripServiceTest {

  private static final User GUEST = null;
  private static final User UNUSED_USER = null;
  private static final User REGISTERED_USER = new User();
  private static final User ANOTHER_USER = new User();
  private static final Trip TO_BRAZIL = new Trip();
  private static final Trip TO_LONDON = new Trip();

  private TripService tripService;

  @BeforeEach
  void setUp() {
    tripService = new TestableTripService();
  }

  @Test
  public void should_throw_an_exception_when_user_is_not_logged_in() {
    assertThrows(UserNotLoggedInException.class,
        () -> tripService.getTripsByUser(UNUSED_USER, GUEST));
  }

  @Test
  public void should_not_return_any_trips_when_users_are_not_friends() {
    User friend = UserBuilder.aUser()
        .friendsWith(ANOTHER_USER)
        .withTrips(TO_BRAZIL)
        .build();

    List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERED_USER);

    assertThat(friendTrips.size(), is(0));
  }

  @Test
  public void should_return_friend_trips_when_users_are_friends() {
    User friend = UserBuilder.aUser()
        .friendsWith(ANOTHER_USER, REGISTERED_USER)
        .withTrips(TO_BRAZIL, TO_LONDON)
        .build();

    List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERED_USER);

    assertThat(friendTrips.size(), is(2));
  }

  public static class UserBuilder {

    private User[] friends = new User[]{};
    private Trip[] trips = new Trip[]{};

    public static UserBuilder aUser() {
      return new UserBuilder();
    }

    public UserBuilder friendsWith(User... friends) {
      this.friends = friends;
      return this;
    }

    public UserBuilder withTrips(Trip... trips) {
      this.trips = trips;
      return this;
    }

    public User build() {
      User user = new User();
      addTripsTo(user);
      addFriendsTo(user);
      return user;
    }

    private void addFriendsTo(User user) {
      for (User friend : friends) {
        user.addFriend(friend);
      }
    }

    private void addTripsTo(User user) {
      for (Trip trip : trips) {
        user.addTrip(trip);
      }
    }
  }

  private class TestableTripService extends TripService {

    @Override
    protected List<Trip> tripsBy(User user) {
      return user.trips();
    }
  }
}
