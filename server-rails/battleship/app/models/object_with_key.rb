module ObjectWithKey
  def init_key
    key = SecureRandom.hex
    until self.class.where(key: key).all.empty?
      key = SecureRandom.hex
    end
    self.key = key
  end
end
