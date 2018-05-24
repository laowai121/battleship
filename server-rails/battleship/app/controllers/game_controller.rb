class GameController < ApplicationController
  before_action :authorize_player, except: [:create, :join]

  def create
    result = @game_manager.create_game(params[:player_name])
    if result[:error]
      render_error(result[:error])
      return
    end

    key = result[:player_key]
    set_key_cookie(key)
    render_success(player_key: key)
  end

  def join
    result = @game_manager.join_game(params[:game_key], params[:player_name])
    if result[:error]
      render_error(result[:error])
      return
    end

    key = result[:player_key].key
    set_key_cookie(key)
    render_success(player_key: key)
  end

  def get_state
    result = @game_manager.get_game_state
    if result[:error]
      render_error(result[:error])
      return
    end
    render_success(
        player: result[:player],
        opponent: result[:opponent],
        ships: result[:ships],
        opponent_ship_notes: result[:opponent_ship_notes]
     )
  end

  def submit_ships
    result = @game_manager.submit_ships(params[:ships])
    if result[:error]
      render_error(result[:error])
      return
    end
    render_success
  end

  def attack
    result = @game_manager.attack(params[:coordinate])
    if result[:error]
      render_error(result[:error])
      return
    end
    render_success
  end

  private

  def authorize_player
    key = params[:player_key] || cookies[:player_key]
    @player = Player.where(key: key).first
    head(403) unless @player

    @game_manager.player = @player
  end

  def set_key_cookie(key)
    cookies.permanent[:player_key] = key
  end

  def render_error(error, params = {})
    render json: {ok: false, error: error}.merge(params)
  end

  def render_success(params = {})
    render json: {ok: true}.merge(params)
  end
end
