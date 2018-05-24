class GameManager
  attr_accessor :player

  def initialize
    @player = nil
  end

  def create_game(player_name)
    result = {error: nil}

    game = Game.new
    player = Player.new(name: player_name)

    begin
      game.transaction do
        player.save!
        game.join(player)
        game.save!

        result[:player_key] = player.key
      end
    rescue
      result[:error] = game.errors.full_messages.first if game.errors.any?
      result[:error] ||= player.errors.full_messages.first if player.errors.any?
      result[:error] ||= 'Unable to create the game'
    end

    result
  end

  def join_game(game_key, player_name)
    result = {error: nil}

    game = Game.where(key: game_key).first
    unless game
      result[:error] = 'Game with this key doesn\'t exist'
      return result
    end

    player = Player.new(name: player_name)

    begin
      game.transaction do
        unless game.accepts_more_players?
          result[:error] = 'This game has already started'
          return result
        end

        player.save!
        game.join(player)
        game.save!

        result[:player_key] = player.key
      end
    rescue
      result[:error] = game.errors.full_messages.first if game.errors.any?
      result[:error] ||= player.errors.full_messages.first if player.errors.any?
      result[:error] ||= 'Unable to join the game'
    end

    result
  end

  def submit_ships(ship_data)
    result = {error: nil}

    if ships.submitted?
      result[:error] = 'You have already submitted the ships'
      return result
    end

    parsed_ships = ShipParser.new.parse(ship_data)
    unless parsed_ships
      result[:error] = 'Invalid ships'
      return result
    end

    parsed_ships.each do |ship|
      ships << ship
    end

    begin
      player.transaction { player.save! }
    rescue
      result[:error] = 'I am sorry...'
    end

    result
  end

  def get_game_state
    result = {error: nil}

    result[:player] = {
        id: player.id,
        name: player.name
    }

    result[:opponent] = opponent ? {
        id: opponent.id,
        name: opponent.name
    } : nil

    result[:ships] = ships.to_json
    result[:opponent_ship_notes] = player.opponent_ship_notes.to_json

    result
  end

  def attack(coordinate)
    return {error: 'Your opponent has still not joined the game'} unless opponent
    return {error: 'You have not submitted ships yet' } unless ships.submitted?
    return {error: 'Your opponent has not submitted ships yet'} unless opponent.ships.submitted?
    return {error: 'The game has already finished. You have lost'} unless ships.alive?
    return {error: 'The game has already finished. You have won' } unless opponent.ships.alive?

    coordinate = Coordinate.parse(coordinate)
    return {error: 'Invalid coordinate'} unless coordinate

    result = {error: nil}
    if opponent.ships.attack(coordinate)
      opponent_ship_notes.save_hit(coordinate)
      result[:hit] = true
      result[:end_of_game] = !opponent.ships.alive?
    else
      opponent_ship_notes.save_miss(coordinate)
      result[:hit] = false
    end

    begin
      Player.transaction do
        player.save!
        opponent.save!
      end
    rescue
      result = {error: 'Something that should not happen has happened'}
    end

    result
  end

  private

  def game
    player&.game
  end

  def opponent
    player&.opponent
  end

  def ships
    player&.ships
  end

  def opponent_ship_notes
    player&.opponent_ship_notes
  end
end
