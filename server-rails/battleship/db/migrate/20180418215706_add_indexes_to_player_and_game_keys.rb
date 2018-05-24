class AddIndexesToPlayerAndGameKeys < ActiveRecord::Migration[5.1]
  def change
    add_index :games, :key
    add_index :players, :key
  end
end
