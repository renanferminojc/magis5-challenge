package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;

public interface DrinkService {
  DrinkGetResponse findById(final String id);

  List<DrinkGetResponse> findAll();

  DrinkPostResponse save(final DrinkPostRequest drink);
}
