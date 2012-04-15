class Ingredient < ActiveRecord::Base
  #attr_accessible :name, :item, :id
  has_many :item_ingredients
  has_many :item,  :through => :item_ingredients
  validates_uniqueness_of :name
end
