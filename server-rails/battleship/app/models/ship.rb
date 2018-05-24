class ShipSegment
  attr_reader :coordinate

  def initialize(coordinate, alive = true)
    @coordinate = coordinate
    @alive = alive
  end

  def alive?
    @alive
  end

  def to_json
    {coordinate: @coordinate.value, alive: @alive}
  end

  def kill!
    @alive = false
  end
end

class Ship
  attr_reader :segments

  def initialize(segments = [])
    @segments = segments
  end

  def alive?
    @segments.any? { |segment| segment.alive? }
  end

  def each
    @segments.each { |segment| yield segment }
  end

  def [](segment_index)
    @segments[segment_index]
  end

  def length
    @segments.length
  end

  def <<(segment)
    @segments << segment
  end

  def to_json
    @segments.map { |segment| segment.to_json }
  end

  alias size length
end