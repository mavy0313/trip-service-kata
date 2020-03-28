package org.craftedsw.tripservicekata.trip;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

public class TripDAOTest {

  @Test
  void should_throw_exception_when_retrieving_user_trips() {
    assertThrows(CollaboratorCallException.class,
        () -> new TripDAO().tripsBy(new User()));
  }
}
