class AddBattlefieldsToPlayerAndGameModels < ActiveRecord::Migration[5.1]
  def change
    add_column :games, :battlefield_a, :text
    add_column :games, :battlefield_b, :text
    add_column :players, :opponent_battlefield_notes, :text
  end
end
