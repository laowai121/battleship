class ApplicationController < ActionController::Base
  protect_from_forgery with: :exception

  before_action :prepare

  def index
    head(200)
  end

  def prepare
    @game_manager = GameManager.new
  end
end
