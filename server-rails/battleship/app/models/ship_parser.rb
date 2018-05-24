class BattlefieldBuilder
  def initialize
    @cells = 10.times.collect { 10.times.collect { false } }
  end

  def add_segment(coordinate)
    if !coordinate || self[coordinate] || !pre_validate_position(coordinate)
      return false
    end
    self[coordinate] = true
    true
  end

  def collect_ships
    @pointer = Coordinate.new(0, 0)
    ships = []
    while (ship = next_ship)
      # deleting ship segments from the battlefield
      ship.each { |segment| self[segment.coordinate] = false }
      ships << ship
    end
    ships
  end

  private

  def [](coordinate)
    @cells[coordinate.row][coordinate.col]
  end

  def []=(coordinate, value)
    @cells[coordinate.row][coordinate.col] = value
  end

  def pre_validate_position(coordinate)
    neighbours = side_neighbouring_segments(coordinate)
    angle_neighbouring_segments(coordinate).empty? &&
        (neighbours.length < 2 ||
            (neighbours.length == 2 &&
                ((neighbours[:top] && neighbours[:bottom]) ||
                    (neighbours[:left] && neighbours[:right]))))
  end

  def side_neighbouring_segments(coordinate)
    result = {}
    neighbours = coordinate.side_neighbours
    result[:top] = true if neighbours[:top] && self[neighbours[:top]]
    result[:bottom] = true if neighbours[:bottom] && self[neighbours[:bottom]]
    result[:left] = true if neighbours[:left] && self[neighbours[:left]]
    result[:right] = true if neighbours[:right] && self[neighbours[:right]]
    result
  end

  def angle_neighbouring_segments(coordinate)
    result = {}
    neighbours = coordinate.angle_neighbours
    result[:top_left] = true if neighbours[:top_left] && self[neighbours[:top_left]]
    result[:top_right] = true if neighbours[:top_right] && self[neighbours[:top_right]]
    result[:bottom_left] = true if neighbours[:bottom_left] && self[neighbours[:bottom_left]]
    result[:bottom_right] = true if neighbours[:bottom_right] && self[neighbours[:bottom_right]]
    result
  end

  def next_ship
    coordinate = next_segment_coordinate
    return nil unless coordinate

    ship = Ship.new([ShipSegment.new(coordinate)])
    segments = get_segments_from(coordinate.side_neighbours[:right], true)
    segments ||= get_segments_from(coordinate.side_neighbours[:bottom], false)
    segments.each { |segment| ship << segment } if segments
    ship
  end

  def get_segments_from(coordinate, horizontal = true)
    segments = []
    while coordinate && self[coordinate]
      segments << ShipSegment.new(coordinate)
      coordinate = horizontal ?
                       coordinate.right_neighbour :
                       coordinate.bottom_neighbour
    end
    segments.empty? ? nil : segments
  end

  def next_segment_coordinate
    coordinate = nil
    while @pointer && !coordinate
      coordinate = @pointer if self[@pointer]
      @pointer = @pointer.next
    end
    coordinate
  end
end

class ShipParser
  def parse(ship_data)
    return nil unless ship_data && ship_data.is_a?(String)
    segments = ship_data.gsub(/[^A-Ja-j\d,]/, '').downcase.split(',')
    return nil unless segments.length == 20

    battlefield = BattlefieldBuilder.new

    error = false
    segments.each do |segment_coordinate|
      unless battlefield.add_segment(Coordinate.parse(segment_coordinate))
        error = true
        break
      end
    end
    return nil if error

    ships = battlefield.collect_ships
    validate_ships(ships) ? Ships.new(ships) : nil
  end

  private

  def validate_ships(ships)
    ships.size == 10 &&
        ships.select { |ship| ship.size == 1 }.size == 4 &&
        ships.select { |ship| ship.size == 2 }.size == 3 &&
        ships.select { |ship| ship.size == 3 }.size == 2 &&
        ships.select { |ship| ship.size == 4 }.size == 1
  end
end