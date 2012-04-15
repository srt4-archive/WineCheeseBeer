class Item < ActiveRecord::Base
  has_many :item_ingredients
  has_many :ingredients, :through => :item_ingredients
end
