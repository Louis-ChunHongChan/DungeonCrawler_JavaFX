package sample;

import gameobjects.RoomExit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maze {

    public static final int MIN_DISTANCE = 7;

    private Graph<IRoom> backingGraph;
    private Map<RoomExit, RoomExit> exitConnections;
    private IRoom start;
    private IRoom end;

    public Maze(List<IRoom> rooms, IRoom start, IRoom end) {
        if (rooms == null) {
            throw new IllegalArgumentException("List of rooms cannot be null");
        }
        this.start = start;
        this.end = end;
        Map<IRoom, Integer> roomConnections = new HashMap<>();
        for (IRoom r : rooms) {
            roomConnections.put(r, r.getNumExits());
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and/or end cannot be null");
        }
        if (!roomConnections.containsKey(start) || !roomConnections.containsKey(end)) {
            throw new IllegalArgumentException("Start and/or end must be in rooms");
        }
        if (!Graph.validateCandidate(roomConnections)) {
            throw new IllegalArgumentException("Unable to create valid maze with specified rooms");
        }
        do {
            backingGraph = Graph.makeRandomGraph(roomConnections, false);
        } while (backingGraph == null
                || backingGraph.shortestPathLength(start, end) < MIN_DISTANCE);
        exitConnections = new HashMap<>();

        Map<IRoom, Map<IRoom, Integer>> available = new HashMap<>();

        for (IRoom r1 : backingGraph.getNodes()) {
            Map<IRoom, Integer> roomAvailable = new HashMap<>();
            for (IRoom r2 : backingGraph.getConnections(r1)) {
                if (!roomAvailable.containsKey(r2)) {
                    roomAvailable.put(r2, 1);
                } else {
                    roomAvailable.put(r2, roomAvailable.get(r2) + 1);
                }
            }
            available.put(r1, roomAvailable);
        }

        for (IRoom fromR : backingGraph.getNodes()) {
            for (RoomExit fromE : fromR.getExits()) {
                if (exitConnections.containsKey(fromE)) {
                    continue;
                }
                roomSearch: for (IRoom toR : backingGraph.getConnections(fromR)) {
                    for (RoomExit toE : toR.getExits()) {
                        if (exitConnections.containsKey(toE)
                                || !available.get(fromR).containsKey(toR)) {
                            continue;
                        }
                        exitConnections.put(fromE, toE);
                        exitConnections.put(toE, fromE);
                        if (available.get(fromR).get(toR) == 1) {
                            available.get(fromR).remove(toR);
                            available.get(toR).remove(fromR);
                        } else {
                            available.get(fromR).put(toR, available.get(fromR).get(toR) - 1);
                            available.get(toR).put(fromR, available.get(toR).get(fromR) - 1);
                        }
                        break roomSearch;
                    }
                }
            }
        }
    }

    public RoomExit getNextRoom(RoomExit exit) {
        if (exit == null) {
            throw new IllegalArgumentException("Exit cannot be null");
        }
        if (!backingGraph.containsNode(exit.getRoom())) {
            throw new IllegalArgumentException("Exit's room is not in this maze");
        }
        return exitConnections.get(exit);
    }

    public IRoom getStart() {
        return start;
    }
    public IRoom getEnd() {
        return end;
    }
    public int getNumRooms() {
        return backingGraph.getSize();
    }

}
