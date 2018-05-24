class Coordinate
  attr_reader :row, :col

  def initialize(row, col)
    raise 'Invalid coordinates' if row < 0 || row > 9 || col < 0 || col > 9
    @row = row
    @col = col
  end

  def ==(another)
    another.is_a?(Coordinate) && another.row == @row && another.col == @col
  end

  def value
    (row + 1).to_s + (col + 'a'.ord).chr
  end

  def side_neighbours
    result = {}
    result[:top] = Coordinate.new(@row - 1, @col) unless top_row?
    result[:bottom] = Coordinate.new(@row + 1, @col) unless bottom_row?
    result[:left] = Coordinate.new(@row, @col - 1) unless left_col?
    result[:right] = Coordinate.new(@row, @col + 1) unless right_col?
    result
  end

  def angle_neighbours
    result = {}
    unless top_row?
      result[:top_left] = Coordinate.new(@row - 1, @col - 1) unless left_col?
      result[:top_right] = Coordinate.new(@row - 1, @col + 1) unless right_col?
    end
    if row < 9
      result[:bottom_left] = Coordinate.new(@row + 1, @col - 1) unless left_col?
      result[:bottom_right] = Coordinate.new(@row + 1, @col + 1) unless right_col?
    end
    result
  end

  def bottom_neighbour
    bottom_row? ? nil : Coordinate.new(@row + 1, @col)
  end

  def right_neighbour
    right_col? ? nil : Coordinate.new(@row, @col + 1)
  end

  def next
    right_neighbour || (bottom_row? ? nil : Coordinate.new(@row + 1, 0))
  end

  def top_row?
    @row == 0
  end

  def bottom_row?
    @row == 9
  end

  def left_col?
    @col == 0
  end

  def right_col?
    @col == 9
  end

  def self.parse(coordinate_str)
    return nil unless coordinate_str.is_a?(String)

    # we are going to use commas for separating coordinates,
    # so we don't want to allow them in coordinate strings
    # an attempt to do so will result in invalid coordinate returned (nil)
    coordinate_str = coordinate_str.gsub(/[^A-Ja-j\d,]/, '').downcase
    return nil if coordinate_str.length < 2 || coordinate_str.length > 3

    c1 = coordinate_str[0]
    c2 = coordinate_str[1]
    c3 = coordinate_str.length == 3 ? coordinate_str[2] : nil
    row = col = nil

    if ('a'..'j').include?(c1)
      if ('1'..'9').include?(c2)
        if c2 == '1' && c3 == '0'
          row = '10'
          col = c1
        elsif !c3
          row = c2
          col = c1
        end
      end
    elsif ('1'..'9').include?(c1)
      if c1 == '1' && c2 == '0' && ('a'..'j').include?(c3)
        row = '10'
        col = c3
      elsif ('a'..'j').include?(c2) && !c3
        row = c1
        col = c2
      end
    end

    if row && col
      Coordinate.new(row.to_i - 1, col.ord - 'a'.ord)
    else
      nil
    end
  end
end