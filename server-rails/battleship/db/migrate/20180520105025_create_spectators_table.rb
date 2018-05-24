class CreateSpectatorsTable < ActiveRecord::Migration[5.1]
  def change
    create_table :spectators do |t|
      t.string "key"
      t.string "name"
      t.integer :game_id
    end
  end
end
