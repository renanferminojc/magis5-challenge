package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;

public interface SectionService {
  SectionGetResponse findById(final String id);

  List<SectionGetResponse> findAll();

  SectionPostResponse save();
}
