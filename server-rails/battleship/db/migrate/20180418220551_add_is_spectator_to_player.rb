class AddIsSpectatorToPlayer < ActiveRecord::Migration[5.1]
  def change
    add_column :players, :is_spectator, :boolean
  end
end
