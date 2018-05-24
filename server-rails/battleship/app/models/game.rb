class Game < ApplicationRecord
  belongs_to :player_a, class_name: 'Player', optional: true
  belongs_to :player_b, class_name: 'Player', optional: true

  include ObjectWithKey

  def spectators
    self.players.where(is_spectator: true)
  end

  def accepts_more_players?
    !player_a || !player_b
  end

  def join(player)
    if player_a
      self.player_b = player
    else
      self.player_a = player
    end
  end

  before_create do
    init_key
  end
end
