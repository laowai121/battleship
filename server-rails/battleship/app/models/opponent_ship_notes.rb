class ShipNote
  def initialize(hit)
    @hit = hit
  end

  def hit?
    @hit
  end
end

class OpponentShipNotes
  def initialize
    @cells = 10.times.collect { 10.times.collect { nil } }
  end

  def save_hit(coordinate)
    save_note(coordinate, true)
  end

  def save_miss(coordinate)
    save_note(coordinate, false)
  end

  def [](coordinate)
    @cells[coordinate.row][coordinate.col]
  end

  def []=(coordinate, value)
    @cells[coordinate.row][coordinate.col] = value
  end

  private

  def save_note(coordinate, hit)
    # don't overwrite notes!
    unless self[coordinate]
      self[coordinate] = ShipNote.new(hit)
    end
  end
end
