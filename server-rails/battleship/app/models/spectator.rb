class Spectator < ApplicationRecord
  validates :name, presence: true, length: {minimum: 1, maximum: 20}

  include ObjectWithKey

  before_create do
    init_key
  end
end
