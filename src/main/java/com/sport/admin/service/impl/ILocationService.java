package com.sport.admin.service.impl;

import com.sport.admin.entity.Location;

import java.util.List;

public interface ILocationService {

    public List<Location> listAll();

    public Location save(Location location);
}
