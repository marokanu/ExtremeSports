package com.sport.admin.service.impl;

import com.sport.admin.entity.Location;
import com.sport.admin.error.LocationNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILocationService {

    public List<Location> listAll();

    public Location save(Location location);

    public String checkUnique(Integer id, String name);

    public void delete(Integer id) throws LocationNotFoundException;

    public Location get(Integer id) throws LocationNotFoundException;

    public Page<Location> listByPage(int pageNum, String sortField, String sortDir, String keyword);
}
