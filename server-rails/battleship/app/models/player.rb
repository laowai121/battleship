class Player < ApplicationRecord
  validates :name, presence: true, length: {minimum: 1, maximum: 20}

  include ObjectWithKey

  def game
    Game.where(player_a: self).or(Game.where(player_b: self)).first
  end

  before_create do
    init_key
    self.ships = Ships.new
    self.opponent_ship_notes = OpponentShipsNotes.new
  end
end
