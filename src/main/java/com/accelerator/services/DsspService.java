package com.accelerator.services;

import java.util.List;
import java.util.SortedMap;

public interface DsspService {

    SortedMap<Double, Character> getDsspContext(List<String> pdbFile, String chain);
}
