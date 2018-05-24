class MoveBattlefieldFromGameToPlayer < ActiveRecord::Migration[5.1]
  def change
    remove_column :games, :battlefield_a
    remove_column :games, :battlefield_b
    add_column :players, :battlefield, :text
  end
end
