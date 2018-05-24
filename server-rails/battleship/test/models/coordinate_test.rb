require 'test_helper'

class CoordinateTest < ActiveSupport::TestCase
  COORD = Coordinate.new(2, 3)


  # constructor

  test 'constructor 1' do
    c = Coordinate.new(0, 0)
    assert c.row == 0 && c.col == 0 && c == Coordinate.new(0, 0)
  end

  test 'constructor 2' do
    c = Coordinate.new(4, 3)
    assert c.row == 4 && c.col == 3 && c == Coordinate.new(4, 3)
  end

  test 'invalid col 1' do
    exception = assert_raises(RuntimeError) { Coordinate.new(2, 10) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid col 2' do
    exception = assert_raises(RuntimeError) { Coordinate.new(0, -1) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid coords 1' do
    exception = assert_raises(RuntimeError) { Coordinate.new(121, 19) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid coords 2' do
    exception = assert_raises(RuntimeError) { Coordinate.new(10, 4) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid coords 3' do
    exception = assert_raises(RuntimeError) { Coordinate.new(-5, -6) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid coords 4' do
    exception = assert_raises(RuntimeError) { Coordinate.new(10, 10) }
    assert exception.message == 'Invalid coordinates'
  end

  test 'invalid coords 5' do
    exception = assert_raises(RuntimeError) { Coordinate.new(-2, 4) }
    assert exception.message == 'Invalid coordinates'
  end


  # row and col

  test 'row and col' do
    assert COORD.row == 2 && COORD.col == 3
  end


  # ==

  test 'equal to self' do
    assert COORD == COORD
  end

  test 'equal to identical coordinate' do
    assert COORD == Coordinate.new(2, 3)
  end

  test 'equal to parsed identical coordinate' do
    assert COORD == Coordinate.parse("d3")
  end

  test 'never equal to object of wrong data type' do
    assert COORD != nil && COORD != [2, 3] && COORD != '' &&
               COORD != 6 && COORD != '3d' && COORD != 'd3'
  end

  test 'not equal to different coordinate 1' do
    assert COORD != Coordinate.new(0, 0)
  end

  test 'not equal to different coordinate 2' do
    assert COORD != Coordinate.new(2, 4)
  end

  test 'not equal to different coordinate 3' do
    assert COORD != Coordinate.new(1, 3)
  end

  test 'not equal to different parsed coordinate' do
    assert COORD != Coordinate.parse('2c')
  end


  # value

  test 'value' do
    assert COORD.value == '3d'
  end

  test 'row goes before column' do
    assert Coordinate.parse('d3').value == '3d'
  end

  test 'should be equal to self after parsing own value' do
    assert Coordinate.parse(COORD.value) == COORD
  end


  # side_neighbours

  test 'side neighbours on angles' do
    n1 = Coordinate.new(0, 0).side_neighbours
    n2 = Coordinate.new(0, 9).side_neighbours
    n3 = Coordinate.new(9, 0).side_neighbours
    n4 = Coordinate.new(9, 9).side_neighbours
    assert n1.size == 2 && n2.size == 2 && n3.size == 2 && n4.size == 2 &&
               n1[:bottom] == Coordinate.new(1, 0) &&
               n1[:right] == Coordinate.new(0, 1) &&
               n2[:left] == Coordinate.new(0, 8) &&
               n2[:bottom] == Coordinate.new(1, 9) &&
               n3[:top] == Coordinate.new(8, 0) &&
               n3[:right] == Coordinate.new(9, 1) &&
               n4[:top] == Coordinate.new(8, 9) &&
               n4[:left] == Coordinate.new(9, 8)
  end

  test 'side neighbours on borders' do
    n1 = Coordinate.new(0, 1).side_neighbours
    n2 = Coordinate.new(5, 9).side_neighbours
    assert n1.size == 3 && n2.size == 3 &&
               n1[:left] == Coordinate.new(0, 0) &&
               n1[:bottom] == Coordinate.new(1, 1) &&
               n1[:right] == Coordinate.new(0, 2) &&
               n2[:top] == Coordinate.new(4, 9) &&
               n2[:left] == Coordinate.new(5, 8) &&
               n2[:bottom] == Coordinate.new(6, 9)
  end

  test 'random side neighbours' do
    n1 = Coordinate.new(8, 8).side_neighbours
    n2 = Coordinate.new(4, 3).side_neighbours
    n3 = Coordinate.new(3, 8).side_neighbours
    assert n1.size == 4 && n2.size == 4 && n3.size == 4 &&
               n1[:top] == Coordinate.new(7, 8) &&
               n1[:left] == Coordinate.new(8, 7) &&
               n1[:right] == Coordinate.new(8, 9) &&
               n1[:bottom] == Coordinate.new(9, 8) &&
               n2[:top] == Coordinate.new(3, 3) &&
               n2[:left] == Coordinate.new(4, 2) &&
               n2[:right] == Coordinate.new(4, 4) &&
               n2[:bottom] == Coordinate.new(5, 3) &&
               n3[:top] == Coordinate.new(2, 8) &&
               n3[:left] == Coordinate.new(3, 7) &&
               n3[:right] == Coordinate.new(3, 9) &&
               n3[:bottom] == Coordinate.new(4, 8)
  end


  # angle_neighbours

  test 'angle neighbours on angles' do
    n1 = Coordinate.new(0, 0).angle_neighbours
    n2 = Coordinate.new(0, 9).angle_neighbours
    n3 = Coordinate.new(9, 0).angle_neighbours
    n4 = Coordinate.new(9, 9).angle_neighbours
    assert n1.size == 1 && n2.size == 1 && n3.size == 1 && n4.size == 1 &&
               n1[:bottom_right] == Coordinate.new(1, 1) &&
               n2[:bottom_left] == Coordinate.new(1, 8) &&
               n3[:top_right] == Coordinate.new(8, 1) &&
               n4[:top_left] == Coordinate.new(8, 8)
  end

  test 'angle neighbours on borders' do
    n1 = Coordinate.new(0, 1).angle_neighbours
    n2 = Coordinate.new(5, 9).angle_neighbours
    assert n1.size == 2 && n2.size == 2 &&
               n1[:bottom_left] == Coordinate.new(1, 0) &&
               n1[:bottom_right] == Coordinate.new(1, 2) &&
               n2[:top_left] == Coordinate.new(4, 8) &&
               n2[:bottom_left] == Coordinate.new(6, 8)
  end

  test 'random angle neighbours' do
    n1 = Coordinate.new(8, 8).angle_neighbours
    n2 = Coordinate.new(4, 3).angle_neighbours
    n3 = Coordinate.new(3, 8).angle_neighbours
    assert n1.size == 4 && n2.size == 4 && n3.size == 4 &&
               n1[:top_left] == Coordinate.new(7, 7) &&
               n1[:top_right] == Coordinate.new(7, 9) &&
               n1[:bottom_left] == Coordinate.new(9, 7) &&
               n1[:bottom_right] == Coordinate.new(9, 9) &&
               n2[:top_left] == Coordinate.new(3, 2) &&
               n2[:top_right] == Coordinate.new(3, 4) &&
               n2[:bottom_left] == Coordinate.new(5, 2) &&
               n2[:bottom_right] == Coordinate.new(5, 4) &&
               n3[:top_left] == Coordinate.new(2, 7) &&
               n3[:top_right] == Coordinate.new(2, 9) &&
               n3[:bottom_left] == Coordinate.new(4, 7) &&
               n3[:bottom_right] == Coordinate.new(4, 9)

  end


  # parse

  test 'valid coordinate string with random characters' do
    c = Coordinate.parse('_______D___??><3')
    assert c.row == 2 && c.col == 3 && c.value == '3d' &&
               c == COORD && c.value == COORD.value
  end

  test 'coordinate string with extra whitespace' do
    c = Coordinate.parse('    10d')
    assert c.row == 9 && c.col == 3 && c.value == '10d' &&
               c == Coordinate.new(9, 3)
  end

  test 'coordinate string in uppercase with underscore' do
    c = Coordinate.parse('E_10')
    assert c.row == 9 && c.col == 4 && c.value == '10e' &&
               c == Coordinate.new(9, 4)
  end

  test 'nil' do
    assert_nil Coordinate.parse(nil)
  end

  test 'empty string' do
    assert_nil Coordinate.parse('')
  end

  test 'whitespace' do
    assert_nil Coordinate.parse(' ')
  end

  test 'wrong type 1' do
    assert_nil Coordinate.parse(14)
  end

  test 'wrong type 2' do
    assert_nil Coordinate.parse([2, 3])
  end

  test 'coordinate string too short' do
    assert_nil Coordinate.parse('1')
  end

  test 'coordinate string too long' do
    assert_nil Coordinate.parse('1234')
  end

  test 'random string' do
    c = Coordinate.parse('*&TF&^TDSU Ft &SUDF')
    assert c.nil? && c == Coordinate.parse('XYZ')
  end

  test 'valid coordinate between random characters' do
    c = Coordinate.parse('*&TF&^TZXU Wv &SUm1ZX0xOO_?')
    assert c.row == 9 && c.col == 5 && c.value == '10f' &&
               c == Coordinate.new(9, 5) &&
               c == Coordinate.parse('(F-10)')
  end

  test 'chinese characters' do
    assert_nil Coordinate.parse('伯逸')
  end

  test 'invalid coordinate 1' do
    assert_nil Coordinate.parse('2e2')
  end

  test 'invalid coordinate 2' do
    assert_nil Coordinate.parse('2e1')
  end

  test 'invalid coordinate 3' do
    assert_nil Coordinate.parse('dd')
  end

  test 'valid coordinate' do
    c = Coordinate.parse('2ke')
    assert c.row == 1 && c.col == 4 && c.value == '2e' &&
               c == Coordinate.new(1, 4)
  end

  test 'dot allowed' do
    c = Coordinate.parse('2.e')
    assert c.row == 1 && c.col == 4 && c.value == '2e' &&
               c == Coordinate.new(1, 4)
  end

  test 'comma not allowed' do
    assert_nil Coordinate.parse('2,e')
  end

  test 'b12 is invalid' do
    assert_nil Coordinate.parse('b12')
  end

  test '12b is invalid' do
    assert_nil Coordinate.parse('12b')
  end

  test '10b is valid' do
    c = Coordinate.parse('10b')
    assert c.row == 9 && c.col == 1 && c == Coordinate.new(9, 1)
  end

  test 'b10 is valid' do
    c = Coordinate.parse('b10')
    assert c.row == 9 && c.col == 1 && c == Coordinate.new(9, 1)
  end


  # bottom_neighbour

  test 'bottom neighbour in top row 1' do
    original_coord = Coordinate.new(0, 0)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 0 && original_coord.col == 0
    assert c.row == 1 && c.col == 0 && c == Coordinate.new(1, 0) && c == Coordinate.parse('A2')
  end

  test 'bottom neighbour in top row 2' do
    original_coord = Coordinate.new(0, 2)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 0 && original_coord.col == 2
    assert c.row == 1 && c.col == 2 && c == Coordinate.new(1, 2) && c == Coordinate.parse('2-c')
  end

  test 'bottom neighbour in top row 3' do
    original_coord = Coordinate.new(0, 9)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 0 && original_coord.col == 9
    assert c.row == 1 && c.col == 9 && c == Coordinate.new(1, 9) && c == Coordinate.parse('<2J>')
  end

  test 'bottom neighbour in the middle' do
    original_coord = Coordinate.new(5, 5)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 5 && original_coord.col == 5
    assert c.row == 6 && c.col == 5 && c == Coordinate.new(6, 5) && c == Coordinate.parse('7F')
  end

  test 'random bottom neighbour' do
    original_coord = Coordinate.new(7, 9)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 7 && original_coord.col == 9
    assert c.row == 8 && c.col == 9 && c == Coordinate.new(8, 9) && c == Coordinate.parse('(9-J)')
  end

  test 'bottom neighbour in bottom row 1' do
    original_coord = Coordinate.new(9, 3)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 9 && original_coord.col == 3
    assert_nil c
  end

  test 'bottom neighbour in bottom row 2' do
    original_coord = Coordinate.new(9, 8)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 9 && original_coord.col == 8
    assert_nil c
  end

  test 'bottom neighbour for the very last coordinate' do
    original_coord = Coordinate.new(9, 9)
    c = original_coord.bottom_neighbour
    assert original_coord.row == 9 && original_coord.col == 9
    assert_nil c
  end


  # right_neighbour

  test 'right neighbour in top row 1' do
    original_coord = Coordinate.new(0, 0)
    c = original_coord.right_neighbour
    assert original_coord.row == 0 && original_coord.col == 0
    assert c.row == 0 && c.col == 1 && c == Coordinate.new(0, 1) && c == Coordinate.parse('1b')
  end

  test 'right neighbour in top row 2' do
    original_coord = Coordinate.new(0, 2)
    c = original_coord.right_neighbour
    assert original_coord.row == 0 && original_coord.col == 2
    assert c.row == 0 && c.col == 3 && c == Coordinate.new(0, 3) && c == Coordinate.parse('1d')
  end

  test 'right neighbour in top row 3' do
    original_coord = Coordinate.new(0, 8)
    c = original_coord.right_neighbour
    assert original_coord.row == 0 && original_coord.col == 8
    assert c.row == 0 && c.col == 9 && c == Coordinate.new(0, 9) && c == Coordinate.parse('J-1')
  end

  test 'right neighbour in top row 4' do
    original_coord = Coordinate.new(0, 9)
    c = original_coord.right_neighbour
    assert original_coord.row == 0 && original_coord.col == 9
    assert_nil c
  end

  test 'right neighbour in the middle' do
    original_coord = Coordinate.new(5, 5)
    c = original_coord.right_neighbour
    assert original_coord.row == 5 && original_coord.col == 5
    assert c.row == 5 && c.col == 6 && c == Coordinate.new(5, 6) && c == Coordinate.parse('[6.G]')
  end

  test 'random right neighbour' do
    original_coord = Coordinate.new(7, 9)
    c = original_coord.right_neighbour
    assert original_coord.row == 7 && original_coord.col == 9
    assert_nil c
  end

  test 'right neighbour in bottom row' do
    original_coord = Coordinate.new(9, 8)
    c = original_coord.right_neighbour
    assert original_coord.row == 9 && original_coord.col == 8
    assert c.row == 9 && c.col == 9 && c == Coordinate.new(9, 9) && c == Coordinate.parse('>>> 1_0_J <<<')
  end

  test 'right neighbour for the very last coordinate' do
    original_coord = Coordinate.new(9, 9)
    c = original_coord.right_neighbour
    assert original_coord.row == 9 && original_coord.col == 9
    assert_nil c
  end


  # next

  test 'next 1' do
    original_coord = Coordinate.new(0, 0)
    c = original_coord.next
    assert original_coord.row == 0 && original_coord.col == 0
    assert c.row == 0 && c.col == 1 && c == Coordinate.new(0, 1)
  end

  test 'next 2' do
    original_coord = Coordinate.new(0, 2)
    c = original_coord.next
    assert original_coord.row == 0 && original_coord.col == 2
    assert c.row == 0 && c.col == 3 && c == Coordinate.new(0, 3)
  end

  test 'next 3' do
    original_coord = Coordinate.new(0, 8)
    c = original_coord.next
    assert original_coord.row == 0 && original_coord.col == 8
    assert c.row == 0 && c.col == 9 && c == Coordinate.new(0, 9)
  end

  test 'next 4' do
    original_coord = Coordinate.new(0, 9)
    c = original_coord.next
    assert original_coord.row == 0 && original_coord.col == 9
    assert c.row == 1 && c.col == 0 && c == Coordinate.new(1, 0)
  end

  test 'next 5' do
    original_coord = Coordinate.new(1, 0)
    c = original_coord.next
    assert original_coord.row == 1 && original_coord.col == 0
    assert c.row == 1 && c.col == 1 && c == Coordinate.new(1, 1)
  end

  test 'next 6' do
    original_coord = Coordinate.new(1, 8)
    c = original_coord.next
    assert original_coord.row == 1 && original_coord.col == 8
    assert c.row == 1 && c.col == 9 && c == Coordinate.new(1, 9)
  end

  test 'next 7' do
    original_coord = Coordinate.new(1, 9)
    c = original_coord.next
    assert original_coord.row == 1 && original_coord.col == 9
    assert c.row == 2 && c.col == 0 && c == Coordinate.new(2, 0)
  end

  test 'next 8' do
    original_coord = Coordinate.new(5, 5)
    c = original_coord.next
    assert original_coord.row == 5 && original_coord.col == 5
    assert c.row == 5 && c.col == 6 && c == Coordinate.new(5, 6)
  end

  test 'next 9' do
    original_coord = Coordinate.new(7, 9)
    c = original_coord.next
    assert original_coord.row == 7 && original_coord.col == 9
    assert c.row == 8 && c.col == 0 && c == Coordinate.new(8, 0)
  end

  test 'next 10' do
    original_coord = Coordinate.new(8, 1)
    c = original_coord.next
    assert original_coord.row == 8 && original_coord.col == 1
    assert c.row == 8 && c.col == 2 && c == Coordinate.new(8, 2)
  end

  test 'next 11' do
    original_coord = Coordinate.new(8, 9)
    c = original_coord.next
    assert original_coord.row == 8 && original_coord.col == 9
    assert c.row == 9 && c.col == 0 && c == Coordinate.new(9, 0)
  end

  test 'next 12' do
    original_coord = Coordinate.new(9, 1)
    c = original_coord.next
    assert original_coord.row == 9 && original_coord.col == 1
    assert c.row == 9 && c.col == 2 && c == Coordinate.new(9, 2)
  end

  test 'next 13' do
    original_coord = Coordinate.new(9, 8)
    c = original_coord.next
    assert original_coord.row == 9 && original_coord.col == 8
    assert c.row == 9 && c.col == 9 && c == Coordinate.new(9, 9)
  end

  test 'next 14' do
    original_coord = Coordinate.new(9, 9)
    c = original_coord.next
    assert original_coord.row == 9 && original_coord.col == 9
    assert_nil c
  end


  # top_row?

  test 'top_row? for top left' do
    assert Coordinate.new(0, 0).top_row?
  end

  test 'top_row? for top middle' do
    assert Coordinate.new(0, 5).top_row?
  end

  test 'top_row? for top right' do
    assert Coordinate.new(0, 9).top_row?
  end

  test 'top_row? for middle left' do
    assert_not Coordinate.new(5, 0).top_row?
  end

  test 'top_row? for middle' do
    assert_not Coordinate.new(5, 5).top_row?
  end

  test 'top_row? for middle right' do
    assert_not Coordinate.new(5, 9).top_row?
  end

  test 'top_row? for bottom left' do
    assert_not Coordinate.new(9, 0).top_row?
  end

  test 'top_row? for bottom middle' do
    assert_not Coordinate.new(9, 5).top_row?
  end

  test 'top_row? for bottom right' do
    assert_not Coordinate.new(9, 9).top_row?
  end


  # bottom_row?

  test 'bottom_row? for top left' do
    assert_not Coordinate.new(0, 0).bottom_row?
  end

  test 'bottom_row? for top middle' do
    assert_not Coordinate.new(0, 5).bottom_row?
  end

  test 'bottom_row? for top right' do
    assert_not Coordinate.new(0, 9).bottom_row?
  end

  test 'bottom_row? for middle left' do
    assert_not Coordinate.new(5, 0).bottom_row?
  end

  test 'bottom_row? for middle' do
    assert_not Coordinate.new(5, 5).bottom_row?
  end

  test 'bottom_row? for middle right' do
    assert_not Coordinate.new(5, 9).bottom_row?
  end

  test 'bottom_row? for bottom left' do
    assert Coordinate.new(9, 0).bottom_row?
  end

  test 'bottom_row? for bottom middle' do
    assert Coordinate.new(9, 5).bottom_row?
  end

  test 'bottom_row? for bottom right' do
    assert Coordinate.new(9, 9).bottom_row?
  end


  # left_col?

  test 'left_col? for top left' do
    assert Coordinate.new(0, 0).left_col?
  end

  test 'left_col? for top middle' do
    assert_not Coordinate.new(0, 5).left_col?
  end

  test 'left_col? for top right' do
    assert_not Coordinate.new(0, 9).left_col?
  end

  test 'left_col? for middle left' do
    assert Coordinate.new(5, 0).left_col?
  end

  test 'left_col? for middle' do
    assert_not Coordinate.new(5, 5).left_col?
  end

  test 'left_col? for middle right' do
    assert_not Coordinate.new(5, 9).left_col?
  end

  test 'left_col? for bottom left' do
    assert Coordinate.new(9, 0).left_col?
  end

  test 'left_col? for bottom middle' do
    assert_not Coordinate.new(9, 5).left_col?
  end

  test 'left_col? for bottom right' do
    assert_not Coordinate.new(9, 9).left_col?
  end


  # right_col?

  test 'right_col? for top left' do
    assert_not Coordinate.new(0, 0).right_col?
  end

  test 'right_col? for top middle' do
    assert_not Coordinate.new(0, 5).right_col?
  end

  test 'right_col? for top right' do
    assert Coordinate.new(0, 9).right_col?
  end

  test 'right_col? for middle left' do
    assert_not Coordinate.new(5, 0).right_col?
  end

  test 'right_col? for middle' do
    assert_not Coordinate.new(5, 5).right_col?
  end

  test 'right_col? for middle right' do
    assert Coordinate.new(5, 9).right_col?
  end

  test 'right_col? for bottom left' do
    assert_not Coordinate.new(9, 0).right_col?
  end

  test 'right_col? for bottom middle' do
    assert_not Coordinate.new(9, 5).right_col?
  end

  test 'right_col? for bottom right' do
    assert Coordinate.new(9, 9).right_col?
  end
end
