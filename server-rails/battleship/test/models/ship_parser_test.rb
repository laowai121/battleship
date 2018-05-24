require 'test_helper'

VALID_SHIPS_STR = '1a,1d,1g,3c,3h,4c,4h,5h,6a,6b,6c,6d,7h,8h,9c,9h,10c,10e,10f,10j'

VALID_SHIPS = '
    abcdefghij
  1 x--x--x---
  2 ----------
  3 --x----x--
  4 --x----x--
  5 -------x--
  6 xxxx------
  7 -------x--
  8 -------x--
  9 --x----x--
 10 --x-xx---x'

EXTRA_SHIP = '
    abcdefghij
  1 x--x--x--x
  2 ----------
  3 --x----x--
  4 --x----x--
  5 -------x--
  6 xxxx------
  7 -------x--
  8 -------x--
  9 --x----x--
 10 --x-xx---x'

TOUCHING_ANGLES = '
    abcdefghij
  1 x--x--x--x
  2 ----------
  3 -------x--
  4 ----x--x--
  5 ----x--x--
  6 xxxx------
  7 -------x--
  8 -------x--
  9 --x----x--
 10 --x-xx---x'

INVALID_SHIP = '
    abcdefghij
  1 x--x--x---
  2 ----------
  3 --x----x--
  4 --x----x--
  5 -------x--
  6 xxx-------
  7 --x----x--
  8 -------x--
  9 --x----x--
 10 --x-xx---x'

INVALID_SHIP_2 = '
    abcdefghij
  1 x--x--x---
  2 ----------
  3 --x----x--
  4 --x----x--
  5 -------x--
  6 xxxx------
  7 -------x--
  8 --x----x--
  9 --x----x--
 10 --x-xx---x'

INVALID_SHIP_3 = '
    abcdefghij
  1 x--x--x---
  2 ----------
  3 --x----x--
  4 --x----x--
  5 -------x--
  6 xxxx------
  7 -------x--
  8 --x----x--
  9 --x----x--
 10 --x-x----x'

VALID_SHIPS_2 = '
    abcdefghij
  1 x--------x
  2 ----------
  3 -----xx-xx
  4 x---------
  5 x--------x
  6 x--------x
  7 x--------x
  8 ----------
  9 ----------
 10 x-xxx-xx-x'

VALID_SHIPS_3 = '
    abcdefghij
  1 ----------
  2 xxxx---x-x
  3 ----------
  4 --x-x-----
  5 --x-x-xx--
  6 --x-x-----
  7 -------x--
  8 --x-xx-x--
  9 ----------
 10 x---------'

VALID_SHIPS_4 = '
    abcdefghij
  1 ---------x
  2 xx----x--x
  3 ---------x
  4 --x---x---
  5 --x-x-----
  6 --x-x---xx
  7 --x-x-----
  8 ----------
  9 x---x---x-
 10 x---------'

VALID_SHIPS_5 = '
    abcdefghij
  1 ----------
  2 -----xx---
  3 ----------
  4 x--x--x--x
  5 x--------x
  6 x--x--x--x
  7 ----------
  8 ---xx--xx-
  9 ----------
 10 xxxx------'

VALID_SHIPS_6 = '
    abcdefghij
  1 -xx-------
  2 -----xxx--
  3 ---------x
  4 -------x--
  5 ----x--x--
  6 ----x--x--
  7 ----x----x
  8 ----x-----
  9 xx----xx--
 10 ---x-----x'

VALID_SHIPS_7 = '
    abcdefghij
  1 -----x----
  2 --x----x--
  3 --x-------
  4 -----x--xx
  5 -x---x----
  6 -----x--xx
  7 -x--------
  8 ------xxx-
  9 ----------
 10 xxxx------'

INVALID_SHIPS_4 = '
    abcdefghij
  1 -----x----
  2 --x----x--
  3 --x-------
  4 -----x--xx
  5 -----x----
  6 --x--x--xx
  7 -x--------
  8 ------xxx-
  9 ----------
 10 xxxx------'

def parse_ships(bf)
  coordinate = Coordinate.new(0, 0)
  str = ''
  bf = bf.gsub(/[^x-]/, '').each_char do |c|
    str << "#{',' unless str.empty?}#{coordinate.value}" if c == 'x'
    coordinate = coordinate.next
  end
  ShipParser.new.parse(str)
end

class ShipParserTest < ActiveSupport::TestCase
  test 'valid ships 1' do
    ships = parse_ships(VALID_SHIPS)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 1_1' do
    ships = ShipParser.new.parse(VALID_SHIPS_STR)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'extra ship' do
    assert_nil parse_ships(EXTRA_SHIP)
  end

  test 'invalid coordinate' do
    assert_nil ShipParser.new.parse(VALID_SHIPS_STR + ',8k')
  end

  test 'duplicate coordinate 1' do
    assert_nil ShipParser.new.parse(VALID_SHIPS_STR + ',10j')
  end

  test 'duplicate coordinate 2' do
    assert_nil ShipParser.new.parse('1a,1d,1g,3c,3h,4c,4h,5h,6a,6b,6c,6d,7h,8h,9c,9h,10c,10e,10f,4c')
  end

  test 'angles should not touch' do
    assert_nil parse_ships(TOUCHING_ANGLES)
  end

  test 'invalid ship' do
    assert_nil parse_ships(INVALID_SHIP)
  end

  test 'invalid ship 2' do
    assert_nil parse_ships(INVALID_SHIP_2)
  end

  test 'invalid ship 3' do
    assert_nil parse_ships(INVALID_SHIP_3)
  end

  test 'valid ships 2' do
    ships = parse_ships(VALID_SHIPS_2)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 3' do
    ships = parse_ships(VALID_SHIPS_3)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 4' do
    ships = parse_ships(VALID_SHIPS_4)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 5' do
    ships = parse_ships(VALID_SHIPS_5)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 6' do
    ships = parse_ships(VALID_SHIPS_6)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'valid ships 7' do
    ships = parse_ships(VALID_SHIPS_7)
    assert_not_nil ships
    assert ships.count == 10
  end

  test 'invalid ships 4' do
    assert_nil parse_ships(INVALID_SHIPS_4)
  end
end