# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20180522145722) do

  create_table "games", force: :cascade do |t|
    t.string "key"
    t.integer "player_a_id"
    t.integer "player_b_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.integer "max_spectators"
    t.index ["key"], name: "index_games_on_key"
  end

  create_table "players", force: :cascade do |t|
    t.string "key"
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.text "opponent_ship_notes"
    t.text "ships"
    t.index ["key"], name: "index_players_on_key"
  end

  create_table "spectators", force: :cascade do |t|
    t.string "key"
    t.string "name"
    t.integer "game_id"
    t.index ["key"], name: "index_spectators_on_key"
  end

end
