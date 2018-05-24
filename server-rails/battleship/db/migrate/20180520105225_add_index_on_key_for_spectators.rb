class AddIndexOnKeyForSpectators < ActiveRecord::Migration[5.1]
  def change
    add_index :spectators, :key
  end
end
