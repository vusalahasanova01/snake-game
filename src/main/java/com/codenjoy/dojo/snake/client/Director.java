package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Direction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Director {

  private Director() {
  }

  public static Direction getDirectionFromSchema(int[][] schema, int startX, int startY, int endX, int endY) {
    try {
      List<AdvancePoint> path = getWay(schema, startX, startY, endX, endY);
      if (path == null || path.isEmpty()) {
        return checkAlive(schema, startX, startY);
      }

      AdvancePoint nextPoint = path.get(1);
      if (startX == endX) {
        if (startY < endY) {
          return Direction.UP;
        } else {
          return Direction.DOWN;
        }
      } else {
        if (startX < endX) {
          return Direction.RIGHT;
        } else {
          return Direction.LEFT;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return checkAlive(schema, startX, startY);
    }
  }

  private static List<AdvancePoint> getWay(int[][] schema, int startedX, int startedY, int endedX, int endedY) {
    int[][] visited = new int[schema.length][schema[0].length];
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};

    Queue<AdvancePoint> queue = new LinkedList<>();
    queue.add(new AdvancePoint(startedX, startedY));
    visited[startedX][startedY] = 1;
    while (!queue.isEmpty()) {
      AdvancePoint point = queue.poll();
      if (point.x == endedX && point.y == endedY) {
        point.path.add(point);
        return point.path;
      }
      for (int i = 0; i < 4; i++) {
        int newX = point.x + dx[i];
        int newY = point.y + dy[i];
        if (newX >= 0 && newX < schema.length && newY >= 0 && newY < schema[0].length && schema[newX][newY] == 0 && visited[newX][newY] == 0) {
          visited[newX][newY] = 1;
          AdvancePoint newPoint = new AdvancePoint(newX, newY);
          newPoint.path.addAll(point.path);
          newPoint.path.add(point);
          queue.add(newPoint);
        }
      }
    }
    return Collections.emptyList();
  }

  private static Direction checkAlive(int[][] matrix, int startX, int startY) {
    if (matrix[startX + 1][startY] != 1) {
      return Direction.RIGHT;
    } else if (matrix[startX - 1][startY] != 1) {
      return Direction.LEFT;
    } else if (matrix[startX][startY + 1] != 1) {
      return Direction.UP;
    } else if (matrix[startX][startY - 1] != 1) {
      return Direction.DOWN;
    }
    return Direction.RIGHT;
  }


  private static class AdvancePoint {

    int x;
    int y;
    List<AdvancePoint> path;

    public AdvancePoint(int x, int y) {
      this.x = x;
      this.y = y;
      path = new LinkedList<>();
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ")";
    }

  }

}
