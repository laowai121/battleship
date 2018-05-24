class AddMaxSpectatorsToGame < ActiveRecord::Migration[5.1]
  def change
    change_table :games do |t|
      t.integer :max_spectators
    end
  end
end
