class RenameBattlefieldToShips < ActiveRecord::Migration[5.1]
  def change
    rename_column :players, :battlefield, :ships
  end
end
