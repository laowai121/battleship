module ApplicationCable
  class Connection < ActionCable::Connection::Base
    identified_by :current_player
  end
end
