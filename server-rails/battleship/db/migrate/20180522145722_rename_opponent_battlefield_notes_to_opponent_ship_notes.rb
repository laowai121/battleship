class RenameOpponentBattlefieldNotesToOpponentShipNotes < ActiveRecord::Migration[5.1]
  def change
    rename_column :players, :opponent_battlefield_notes, :opponent_ship_notes
  end
end
