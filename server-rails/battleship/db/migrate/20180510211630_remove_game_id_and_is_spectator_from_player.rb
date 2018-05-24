class RemoveGameIdAndIsSpectatorFromPlayer < ActiveRecord::Migration[5.1]
  def change
    remove_column :players, :is_spectator
    remove_column :players, :game_id
  end
end
