package g61692.qwirkle.model;

import java.io.Serializable;

/**
 * A record representing a Qwirkle tile, consisting of a color and a shape.
 */
public record Tile(Color color, Shape shape) implements Serializable {
}
