class Ships
  def initialize(ships = [])
    @ships = ships
  end

  def [](ship_index)
    @ships[ship_index]
  end

  def each
    @ships.each { |ship| yield ship }
  end

  def <<(ship)
    @ships << ship
  end

  def count
    @ships.size
  end

  def submitted?
    @ships.size > 0
  end

  def to_json
    @ships.map { |ship| ship.to_json }
  end

  def alive?
    @ships.any? { |ship| ship.alive? }
  end

  def attack(coordinate)
    ship_segment = segment_at(coordinate)
    if ship_segment&.alive?
      ship_segment.kill!
      true
    else
      false
    end
  end

  private

  def segment_at(coordinate)
    found_segment = nil
    @ships.each do |ship|
      found_segment = ship.find { |segment| segment.coordinate == coordinate }
      break if found_segment
    end
    found_segment
  end
end
