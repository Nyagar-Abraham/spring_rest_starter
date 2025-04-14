package org.studyeasy.SpringRestDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringRestDemo.model.Album;
import org.studyeasy.SpringRestDemo.repository.AlbumRepository;

@Service
public class AlbumService {

  @Autowired
  private AlbumRepository albumRepository;

  public Album save(Album album){
    return albumRepository.save(album);
  }
  
}
